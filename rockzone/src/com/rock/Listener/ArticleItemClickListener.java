package com.rock.Listener;

import android.content.Context;
import android.view.View;
import com.rock.Helper.UIHelper;
import com.rock.model.Article;

/**
 * Created by wym on 2014/11/25.
 */
public class ArticleItemClickListener implements View.OnClickListener {
    private Article article;
    private Context context;
    public ArticleItemClickListener(Context context,Article article){
        this.article = article;
        this.context = context;
    }
    @Override
    public void onClick(View view) {
        String id= article.getArticleId();
        UIHelper.showArticleDetailView(context, id,"文章详情");
    }
}
