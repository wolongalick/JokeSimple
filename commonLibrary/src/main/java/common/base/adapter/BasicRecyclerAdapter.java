package common.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import common.base.viewholder.BaseViewHolder;
import common.ui.ICommonOnItemClickListener;

/**
 * 通用的RecyclerAdapter
 * Created by cxw on 2017/2/15.
 */

public abstract class BasicRecyclerAdapter<Model, Holder extends BaseViewHolder> extends RecyclerView.Adapter<Holder> {
    private List<Model> data;
    private Context context;
    private ICommonOnItemClickListener<Holder, Model> iCommonOnItemClickListener;
    private LayoutInflater layoutInflater;

    private static final int TYPE_ITEM = -1;
    private static final int TYPE_FOOTER = -2;
    private boolean isSupportLoadmore;
    private boolean isNoMoreData;
    private Holder footHolder;
    private boolean isHideNoMoreDataView;   //是否隐藏"没有更多数据"的页脚

    public BasicRecyclerAdapter(List<Model> data) {
        this.data = data;
    }

    @Override
    public final int getItemViewType(int position) {
        if (isSupportLoadmore && position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return getItemViewTypeDelay(position);
        }
    }

    protected int getItemViewTypeDelay(int position) {
        return TYPE_ITEM;
    }

    /**
     * 此方法唯一目的就是为了从parent对象中获取context,从而不需要在子类实例化时传入context了,
     * 并用final修饰此方法,禁止子类重写本方法
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public final Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);
        if (isSupportLoadmore && viewType == TYPE_FOOTER) {
            Holder footHolder = onDelayCreateFootViewHolder(parent);

            if (footHolder == null) {
                //holder为null说明子类没有重写父类的onDelayCreateFootViewHolder方法,因此需要由父类(BasicRecyclerAdapter类)自己创建默认的holder
                try {
                    footHolder= createDefaultFootViewHolder(parent);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            this.footHolder=footHolder;
            return footHolder;
        } else {
            return onDelayCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (!isSupportLoadmore || position < getItemCount() - 1) {
            //1.不支持加载更多时,直接按常规流程填充子类holder
            onFillViewByModel(holder, getData().get(position), position);
        }else {
            View ll_loadingView = holder.itemView.findViewById(R.id.ll_loading);
            View noMoreDataView = holder.itemView.findViewById(R.id.ll_noMoreData);
            if(isNoMoreData){
                ll_loadingView.setVisibility(View.GONE);
                noMoreDataView.setVisibility(isHideNoMoreDataView? View.GONE : View.VISIBLE);
            }else {
                ll_loadingView.setVisibility(View.VISIBLE);
                noMoreDataView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 延迟创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract Holder onDelayCreateViewHolder(ViewGroup parent, int viewType);

    public Holder onDelayCreateFootViewHolder(ViewGroup parent) {

        return null;
    }

    private Holder createDefaultFootViewHolder(ViewGroup parent) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        //取出Holder的真实类型
        Type type = parameterizedType.getActualTypeArguments()[1];
        Class<BaseViewHolder> entityClass = (Class<BaseViewHolder>) type;
        Constructor<?> constructor;
        try {
            constructor = entityClass.getConstructor(View.class,boolean.class);
            return (Holder) constructor.newInstance(layoutInflater.inflate(R.layout.item_foot,parent,false),true);
        } catch (NoSuchMethodException e) {
            try {
                constructor=entityClass.getConstructor(BasicRecyclerAdapter.this.getClass(),View.class,boolean.class);
                return (Holder) constructor.newInstance(BasicRecyclerAdapter.this,layoutInflater.inflate(R.layout.item_foot,parent,false),true);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
                String name = ((Class<BaseViewHolder>) type).getName();
                throw new NullPointerException("请提供"+ name +"(View,boolean)类型的构造方法");
            }
        }
    }

    /**
     * 通过数据模型填充视图
     *
     * @param holder
     * @param model
     * @param position
     */
    public abstract void onFillViewByModel(final Holder holder, final Model model, final int position);

    @Override
    public int getItemCount() {
        return (data==null || data.size() == 0) ? 0 : data.size() + (isSupportLoadmore ? 1 : 0);
    }

    public boolean isLastItem(int position){
        return data.size()-1==position;
    }

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public Context getContext() {
        return context;
    }


    public void setiCommonOnItemClickListener(ICommonOnItemClickListener<Holder, Model> iCommonOnItemClickListener) {
        this.iCommonOnItemClickListener = iCommonOnItemClickListener;
    }

    protected void onItemClick(Holder holder, Model model, int position) {
        if (iCommonOnItemClickListener != null) {
            iCommonOnItemClickListener.onItemClick(holder, model, position);
        }
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setSupportLoadmore(boolean supportLoadmore) {
        isSupportLoadmore = supportLoadmore;
    }


    public void noMoreData(){
        isNoMoreData=true;
        if(footHolder!=null){
            footHolder.itemView.findViewById(R.id.ll_loading).setVisibility(View.GONE);
            footHolder.itemView.findViewById(R.id.ll_noMoreData).setVisibility(isHideNoMoreDataView ? View.GONE : View.VISIBLE);
        }
    }

    public void setHideNoMoreDataView(boolean hideNoMoreDataView) {
        isHideNoMoreDataView = hideNoMoreDataView;
    }
}
