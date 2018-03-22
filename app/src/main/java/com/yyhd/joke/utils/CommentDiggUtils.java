package com.yyhd.joke.utils;


import com.yyhd.joke.db.entity.CommentDigg;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/9/10
 * 备注:
 */
public class CommentDiggUtils {

    public static void saveCommentDigg(CommentDigg commentDigg){
    }

    public static CommentDigg getCommentById(String commentId) {
        return null;
    }

    public static boolean isDigged(String commentId){
        CommentDigg commentDigg = getCommentById(commentId);
        if(commentDigg!=null){
            return commentDigg.getIsDigged();
        }
        return false;
    }

    public static int getDiggCount(String commentId){
        CommentDigg commentDigg = getCommentById(commentId);
        if(commentDigg!=null){
            return commentDigg.getDiggCount();
        }
        return 0;
    }

    public static void diggPlus(String commentId, long publicTime){
        CommentDigg commentDigg = getCommentById(commentId);
        if(commentDigg!=null){
            commentDigg.setDiggCount(commentDigg.getDiggCount()+1);
            commentDigg.setIsDigged(true);
        }else {
            commentDigg=new CommentDigg();
            commentDigg.setId(commentId);
            commentDigg.setIsDigged(true);
            commentDigg.setDiggCount(1);//初次赞的个数肯定是1
            commentDigg.setCommentPublicTime(publicTime);
        }
        saveCommentDigg(commentDigg);
    }
}
