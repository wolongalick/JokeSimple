package com.yyhd.joke.module.home.view.adapter;

import android.view.View;

import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.module.home.view.adapter.holder.BaseHomeViewHolder;

import java.util.List;

import common.base.adapter.BasicRecyclerAdapter;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/8/9
 * 备注:
 */
public abstract class BaseHomeAdapter<Model extends DataAllBean, Holder extends BaseHomeViewHolder> extends BasicRecyclerAdapter<Model, Holder> {
    private String lastJokeId;
    private OnClickLastRefreshListener onClickLastRefreshListener;
    private OnClickJokeListener<Model> onClickJokeListener;

    private OnShareModelListener onShareListener;

    public interface OnShareModelListener<Model extends DataAllBean>{
        void onStartShare(Model model);
    }

    public interface OnClickLastRefreshListener {
        void onClickLastRefresh();
    }


    public interface OnClickJokeListener<Model> {
        /**
         * 点击段子的监听
         *
         * @param model            点击的数据模型
         * @param position         点击的索引
         * @param isToCommentPlace 是否滚动到评论区域
         */
        void onClickJoke(Model model, int position, boolean isToCommentPlace);

        /**
         * 点赞
         *
         * @param model
         */
        void onClickDigg(Model model);

        /**
         * 点踩
         *
         * @param model
         */
        void onClickBury(Model model);

        /**
         * 分享
         * @param model
         */
        void onClickShare(Model model);

        /**
         * 对评论点赞
         * @param commentId
         */
        void onClickDiggComment(String commentId);

        /**
         * 不喜欢某个段子
         * @param model
         */
        void onDislikeJoke(Model model);

    }


    public BaseHomeAdapter(List<Model> data) {
        super(data);
    }


    /**
     * 通过数据模型填充视图
     *
     * @param holder
     * @param model
     * @param position
     */
    @Override
    public void onFillViewByModel(final Holder holder, final Model model, final int position) {
//        testGodComment(model, holder,position);

        if(!holder.itemView.hasOnClickListeners()){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickJokeListener != null) {
                        onClickJokeListener.onClickJoke(model, position, false);
                    }
                }
            });
        }

        /*if (TextUtils.equals(lastJokeId, model.getId())) {
            holder.flLastItem.setVisibility(View.VISIBLE);
            if(!holder.flLastItem.hasOnClickListeners()){
                holder.flLastItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onClickLastRefreshListener != null) {
                            onClickLastRefreshListener.onClickLastRefresh();
                        }
                    }
                });
            }
        } else {
            holder.flLastItem.setVisibility(View.GONE);
        }
        UserInfo authorDTO = model.getAuthorDTO();

        *//*显示头像和作者用户名*//*
        if(authorDTO!=null){
            BizUtils.showDecryptContent(holder.nickName, authorDTO.getNickName());
            BizUtils.showAvatar(holder.headPic, authorDTO.getHeadPic());
        }else {
            holder.nickName.setText("未知用户");
            BizUtils.showAvatar(holder.headPic, null);
        }

        if ((BizContant.JokeCode.ODDPHOTO.equals(model.getCategoryCode())
                || BizContant.JokeCode.BEAUTY.equals(model.getCategoryCode()))
                && !TextUtils.isEmpty(model.getTitle())) {
            holder.titleText.setText(model.getTitle());
            holder.titleText.setVisibility(View.VISIBLE);
        } else {
            holder.titleText.setVisibility(View.GONE);
        }


//        ======踩,赞,评论,分享
        if (model.getDiggCount() == 0) {
            model.setDiggCount(BizUtils.getRandomDigg());
        }
        holder.tvDigg.setText(model.getDiggCount() + "");

        if (model.getBuryCount() == 0) {
            model.setBuryCount(BizUtils.getRandomBury());
        }
        holder.tvBury.setText(model.getBuryCount() + "");
        holder.tvComment.setText(model.getCommentNum() + "");
        if (model.getShareCount() == 0) {
            model.setShareCount(BizUtils.getRandomDigg());
        }
        holder.tvShare.setText(model.getShareCount() + "");

        if (model.isDigged()) {
            holder.llDigg.setSelected(true);
            holder.llBury.setSelected(false);
            holder.ivDigg.setImageResource(R.drawable.zan_26);
            holder.ivBury.setImageResource(R.drawable.new_bury);
        } else if (model.isBuryed()) {
            holder.llDigg.setSelected(false);
            holder.llBury.setSelected(true);
            holder.ivDigg.setImageResource(R.drawable.new_digg);
            holder.ivBury.setImageResource(R.drawable.cai_26);
        } else {
            holder.llDigg.setSelected(false);
            holder.llBury.setSelected(false);
            holder.ivDigg.setImageResource(R.drawable.new_digg);
            holder.ivBury.setImageResource(R.drawable.new_bury);
        }

        if(!holder.llDigg.hasOnClickListeners()){
            holder.llDigg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isDigged()) {
                        T.show((Activity) getContext(), "您已经顶过");
                    } else if (model.isBuryed()) {
                        T.show((Activity) getContext(), "您已经踩过");
                    } else {
                        doDigg(holder, model);
                        if (onClickJokeListener != null) {
                            onClickJokeListener.onClickDigg(model);
                        }
                    }
                }
            });
        }

        if(!holder.llBury.hasOnClickListeners()){
            holder.llBury.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isDigged()) {
                        T.show((Activity) getContext(), "您已经顶过");
                    } else if (model.isBuryed()) {
                        T.show((Activity) getContext(), "您已经踩过");
                    } else {
                        doBury(holder, model);
                        if (onClickJokeListener != null) {
                            onClickJokeListener.onClickBury(model);
                        }
                    }
                }
            });
        }

        if(!holder.llComment.hasOnClickListeners()){
            holder.llComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickJokeListener != null) {
                        onClickJokeListener.onClickJoke(model, position, true);
                    }
                }
            });
        }

        if(!holder.llShare.hasOnClickListeners()){
            holder.llShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(model, holder);
                }
            });
        }

        if(!holder.titleText.hasOnClickListeners()){
            holder.titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoJokeDetailActivity(model, position);
                }
            });
        }

        //删除条目
        if(!holder.remove.hasOnClickListeners()){
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData().remove(position);
                    BaseHomeAdapter.this.notifyItemRemoved(position);
                    BaseHomeAdapter.this.notifyItemRangeChanged(position, getData().size() - position);

                    onClickJokeListener.onDislikeJoke(model);
                }
            });
        }*/

    }

    public void share(final Model model, Holder holder) {
        if(onClickJokeListener != null){
            onClickJokeListener.onClickShare(model);
        }
        model.setShareNum(model.getShareNum()+1);
        model.setShareCount(model.getShareCount()+1);
//        holder.tvShare.setText(String.valueOf(model.getShareCount()));
        doShare(model, holder);
    }

    private void doShare(final Model model, Holder holder) {

    }


    /*private void doDigg(final Holder holder, Model model) {
        holder.llDigg.setSelected(true);
        model.setDigged(true);
        DiggAnimUtils.showPlus(holder.tvDiggPlus);
        holder.tvDigg.setText(String.valueOf(Integer.parseInt(holder.tvDigg.getText().toString()) + 1));

        holder.ivDigg.setImageResource(R.drawable.digg_gif);
        ((AnimationDrawable) holder.ivDigg.getDrawable()).start();

        model.setUpVoteNum(model.getUpVoteNum()+1);
        model.setDiggCount(model.getDiggCount()+1);

        doDigg4Lp(model);
    }

    private void doBury(final Holder holder, Model model) {
        holder.llBury.setSelected(true);
        model.setBuryed(true);
        DiggAnimUtils.showPlus(holder.tvBuryPlus);
        holder.tvBury.setText(String.valueOf(Integer.parseInt(holder.tvBury.getText().toString()) + 1));

        holder.ivBury.setImageResource(R.drawable.bury_gif);
        ((AnimationDrawable) holder.ivBury.getDrawable()).start();

        model.setDownVoteNum(model.getDownVoteNum()+1);
        model.setBuryCount(model.getBuryCount()+1);
        doBury4Lp(model);
    }*/

    /**
     * 专门给绿皮用的点赞
     * @param model
     */
    private void doDigg4Lp(Model model){

    }


    /**
     * 专门给绿皮用的点踩
     * @param model
     */
    private void doBury4Lp(Model model){

    }


    public void setLastJokeId(String lastJokeId) {
        this.lastJokeId = lastJokeId;
    }

    public void setOnClickLastRefreshListener(OnClickLastRefreshListener onClickLastRefreshListener) {
        this.onClickLastRefreshListener = onClickLastRefreshListener;
    }

    public void setOnClickJokeListener(OnClickJokeListener onClickJokeListener) {
        this.onClickJokeListener = onClickJokeListener;
    }

    /**
     * 是否有图片
     *
     * @return
     */
    protected abstract boolean isHasPhoto(Model model, Holder holder);


    protected void gotoJokeDetailActivity(Model model, int position) {
        if (onClickJokeListener != null) {
            onClickJokeListener.onClickJoke(model, position, false);
        }
    }

    //神评论
    public void testGodComment(final Model model, final Holder holder, final int jokePosition) {
        /*GodCommentAdapter godCommentAdapter = new GodCommentAdapter(model.getGodComment());
        holder.ry_godComment.setLayoutManager(new LinearLayoutManager(getContext()));
        holder.ry_godComment.setAdapter(godCommentAdapter);
        ItemDecorationUtils.addItemDecoration(holder.ry_godComment,new NormalLLRVDecoration(DensityUtils.dp2px(getContext(),20),getContext().getResources().getDrawable(R.drawable.shape_god_comment)));
        holder.flGodComment.setVisibility(!DataUtils.isEmpty(model.getGodComment()) ? View.VISIBLE : View.GONE);

        holder.ry_godComment.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if (onClickJokeListener != null) {
                        onClickJokeListener.onClickJoke(model, jokePosition, true);
                    }
                }
                return false;
            }
        });

        godCommentAdapter.setOnClickGodCommentListener(new GodCommentAdapter.OnClickGodCommentListener() {
            @Override
            public void onClickGodComment(CommentsBean commentsBean) {
                if (onClickJokeListener != null) {
                    onClickJokeListener.onClickJoke(model, jokePosition, true);
                }
            }

            @Override
            public void onDiggGodComment(CommentsBean commentsBean) {
                if(onClickJokeListener!=null){
                    onClickJokeListener.onClickDiggComment(commentsBean.getId());
                }
            }

            @Override
            public void onShareGodComment() {
                share(model,holder);
            }

            *//**
             * 点击评论中九宫格的其中一个图片
             *
             * @param commentsBean
             * @param photoPosition
             *//*
            @Override
            public void onClickCommentPhoto(CommentsBean commentsBean, int photoPosition) {
            }
        });*/
    }

    public void setOnShareListener(OnShareModelListener onShareListener) {
        this.onShareListener = onShareListener;
    }
}
