package edu.ynu.sl.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;

/**
 * Created by ku on 2015/1/4.
 */
public class LibFragment extends Fragment {
    private WebView webView;   //
    private View progressBar;//
    @InjectView(R.id.frame_container)
    LinearLayout container; //布局容器

    private View web;
    private boolean webViewIsAdd = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        //注入视图
        ButterKnife.inject(this, view);

        //初始webview
        web = LayoutInflater.from(getActivity()).inflate(R.layout.web_view, null);
        webView = (WebView) web.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("http://www.lib.ynu.edu.cn/");


        //添加一个顶视图，以遮丑
        TextView topView = new TextView(getActivity());
        topView.setHeight(50);
        //依次添加视图
        addView(topView, 0);
        addWebView(webView);

    }

    /**
     * 添加
     */
    private void addWebView(WebView webView) {

        if (!webViewIsAdd) {
            addView(web, 1);
        }
    }

    public void removeWebView() {
        if (webViewIsAdd) {
            removeView(web);
        }
    }

    private void removeView(View view) {
        container.removeView(view);
    }

    private void addView(View view, int i) {
        container.addView(view, i);
    }

    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
