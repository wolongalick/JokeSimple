package common.listdata.impl2;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.common.R;
import com.zeropercenthappy.decorationlibrary.NormalLLRVDecoration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import common.base.ViewHelperImpl;
import common.base.adapter.BasicRecyclerAdapter;
import common.base.viewholder.BaseViewHolder;
import common.listdata.api2.IViewListHelper2;
import common.ui.PullLoadRecyclerView;
import common.ui.api.OnPullLoadListener;
import common.ui.datacontent.IEmptyLayout;
import common.ui.datacontent.IFailLayout;
import common.ui.datacontent.SimpleGlobalFrameLayout2;
import common.utils.DataUtils;
import common.utils.DensityUtils;
import common.utils.LoadDataConfig;

/**
 * Created by Alick on 2016/9/25.
 */

public abstract class ViewListHelperImpl2<Model, Holder extends BaseViewHolder, Adapter extends BasicRecyclerAdapter<Model, Holder>> extends ViewHelperImpl implements IViewListHelper2<Model, Holder, Adapter> {
    private final int pageSize = getPageSize();
    private final int firstPage = getFirstPage();

    private int pageNum = LoadDataConfig.DEFAULT_FIRST_PAGE;
    private List<Model> allDataList;
    private Adapter adapter;

//    private PullToRefreshLayout prl;
    protected SimpleGlobalFrameLayout2 simpleGlobalFrameLayout2;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected PullLoadRecyclerView pullLoadRecyclerView;

    /**
     * 初始化列表数据
     */
    @Override
    public void initListData() {
        Type genericSuperclass = getViewClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type type = parameterizedType.getActualTypeArguments()[2];
        Class<Adapter> entityClass = (Class<Adapter>) type;
        Constructor<?>[] constructors = entityClass.getConstructors();
        try {
            allDataList=new ArrayList<>();
            adapter = (Adapter) constructors[0].newInstance(allDataList);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void initListener() {
        pullLoadRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        pullLoadRecyclerView.setAdapter(adapter);

        if (!isCustomItemDecoration()) {
            //如果不需要自定义则使用默认的分割线
            addDefaultItemDecoration();
        }


        simpleGlobalFrameLayout2.setOnClickReloadButton(new IFailLayout.OnClickReloadButtonListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        simpleGlobalFrameLayout2.setOnClickEmptyLayoutListener(new IEmptyLayout.OnClickEmptyLayoutListener() {
            @Override
            public void onClick(View v) {
                onClickEmptyBtn();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.possible_result_points);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = firstPage;
                //明确指出:调用本类(实际是子类)的onRefresh()方法
                ViewListHelperImpl2.this.onRefresh();
            }
        });

        pullLoadRecyclerView.setPullLoadListener(new OnPullLoadListener() {
            @Override
            public void onPullLoad() {
                ViewListHelperImpl2.this.onLoadMore(pageNum);
            }
        });
    }

    /**
     * 添加默认分割线
     */
    private void addDefaultItemDecoration() {
        pullLoadRecyclerView.addItemDecoration(new NormalLLRVDecoration(DensityUtils.dp2px(getContext(),12),getContext().getResources().getDrawable(R.drawable.divider_list_grey_12dp)));
    }


    /**
     * 更新列表数据
     *
     * @param dataList
     */
    @Override
    public void updateData(List<Model> dataList) {
        int size = dataList != null ? dataList.size() : 0;
        //无论此时是刷新结束还是加载更多结束,都要重置swipeRefreshLayout的位置
        swipeRefreshLayout.setRefreshing(false);
        //1.下拉刷新
        if (pageNum == firstPage) {
            //设置刷新成功
//            prl.refreshFinish(PullToRefreshLayout.SUCCEED);
            if (size == 0) {
                //1.1.无数据显示空页面
                showEmptyView();
            } else {
                allDataList = dataList;
                updateListView(true, size);
            }
            //2.加载更多
        } else {
            allDataList.addAll(dataList);
            updateListView(false, size);
            pullLoadRecyclerView.setLoadMoreFinish();
        }
    }

    public void updateData2(List<Model> dataList) {
        //无论此时是刷新结束还是加载更多结束,都要重置swipeRefreshLayout的位置
        swipeRefreshLayout.setRefreshing(false);
        int size = dataList != null ? dataList.size() : 0;
        boolean isRefresh = pageNum == firstPage;

        //无论此时是刷新结束还是加载更多结束,都要重置swipeRefreshLayout的位置
        swipeRefreshLayout.setRefreshing(false);
        //1.下拉刷新
        if (isRefresh) {
            //设置刷新成功
//            prl.refreshFinish(PullToRefreshLayout.SUCCEED);
            if (size == 0) {
                //1.1.无数据显示空页面
                showEmptyView();
            } else {
                allDataList = dataList;
                updateListView(true, size);
            }
            //2.加载更多
        } else {
            allDataList.addAll(dataList);
            updateListView(false, size);
        }
    }




    /**
     * 更新列表空数据
     */
    @Override
    public void updateEmptyData() {
        //1.下拉刷新
        if (pageNum == firstPage) {
//            prl.refreshFinish(PullToRefreshLayout.FAIL);
            if (DataUtils.isEmpty(allDataList)) {
                showFailView();
            } else if (allDataList.isEmpty()) {
                //1.1.无数据显示空页面
                showEmptyView();
            } else {
                showRealView();
            }
            //2.加载更多
        } else {
//            prl.loadmoreFinish(PullToRefreshLayout.FAIL);
        }
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public void resetPageNum() {
        pageNum = getFirstPage();
    }


    @Override
    public List<Model> getAllDataList() {
        return allDataList;
    }

    public SimpleGlobalFrameLayout2 getSimpleGlobalFrameLayout2() {
        return simpleGlobalFrameLayout2;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public PullLoadRecyclerView getPullLoadRecyclerView() {
        return pullLoadRecyclerView;
    }


    /**
     * 更新列表
     *
     * @param isRefresh
     * @param size
     */
    private void updateListView(boolean isRefresh, int size) {
        adapter.setData(allDataList);
        adapter.notifyDataSetChanged();

        //是否由client端决定是否为最后一页
        if (isLastPageDependClient()) {
            //1.是由client端决定
            if (isLastPage()) {
//                prl.loadmoreFinish(PullToRefreshLayout.NO_MORE_DATA_NEXT_PAGE);
            } else {
//                prl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                pageNum++;
            }
        } else {
            //2.不是由client端决定,那么采用默认逻辑:当次获取的数据量小于请求的量,则认为是最后一页
            if (size < pageSize) {
                //加载更多完成(没有更多数据了)
                if(isRefresh){
                    pullLoadRecyclerView.loadmoreFinish(PullLoadRecyclerView.NO_MORE_DATA_FIRST_PAGE);
                }else {
                    pullLoadRecyclerView.loadmoreFinish(PullLoadRecyclerView.NO_MORE_DATA_NEXT_PAGE);
                }
                getAdapter().noMoreData();
            }else {
                pageNum++;
                pullLoadRecyclerView.loadmoreFinish(PullLoadRecyclerView.MAYBE_HAVE_MORE_DATA);
            }
        }

        if (isRefresh) {
            showRealView();
        } else {
            showRealViewWithoutAnimation();
        }
    }
}
