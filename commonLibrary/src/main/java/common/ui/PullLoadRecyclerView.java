package common.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import common.ui.api.OnPullLoadListener;


public class PullLoadRecyclerView extends RecyclerView {

    private OnPullLoadListener onPullLoadListener;
    private boolean isLoadMoring;

    private int state;
    public static final int MAYBE_HAVE_MORE_DATA =0;        //可能有更多数据
    public static final int NO_MORE_DATA_FIRST_PAGE =1;     //首页没有更多数据
    public static final int NO_MORE_DATA_NEXT_PAGE =2;      //次页没有更多数据


    private IOnLoadEndListener iOnLoadEndListener;

    public interface IOnLoadEndListener{
        void onLoadEnd();
    }

    public PullLoadRecyclerView(Context context) {
        super(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if(!canScrollVertically(1)){
            if(!isLoadMoring){
                  isLoadMoring=true;
                switch (state){
                    case NO_MORE_DATA_FIRST_PAGE:
                        //如果在第一页就没有更多数据,则不做任何处理
                        setLoadMoreFinish();
                        break;
                    case NO_MORE_DATA_NEXT_PAGE:
                        //如果在次页没有更多数据,则通知客户端UI,给用户提示
                        if(iOnLoadEndListener!=null){
                            iOnLoadEndListener.onLoadEnd();
                        }
                        setLoadMoreFinish();
                        break;
                    default:
                        if(onPullLoadListener!=null){
                            onPullLoadListener.onPullLoad();
                        }
                }
            }
        }
    }

    public void setPullLoadListener(OnPullLoadListener onPullLoadListener) {
        this.onPullLoadListener = onPullLoadListener;
    }

    public void setOnLoadEndListener(IOnLoadEndListener iOnLoadEndListener) {
        this.iOnLoadEndListener = iOnLoadEndListener;
    }

    public void setLoadMoreFinish() {
        isLoadMoring = false;
    }

    public void loadmoreFinish(int state) {
        this.state=state;
    }
}
