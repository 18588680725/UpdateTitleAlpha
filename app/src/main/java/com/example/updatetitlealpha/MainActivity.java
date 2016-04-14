package com.example.updatetitlealpha;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity
{

	private RelativeLayout rl_title;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT)
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_main);
		initView();
	}

	private View headview;
	private int headerHeight;

	private void initView()
	{
		rl_title = (RelativeLayout) findViewById(R.id.rl_title);
		rl_title.getBackground().setAlpha(0);
		headview = View.inflate(this, R.layout.headview, null);
		listview = (ListView) findViewById(R.id.listview);
		listview.addHeaderView(headview);
		listview.setAdapter(new MyAdapter());
		listview.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				// 判断当前最上面显示的是不是头布局，因为Xlistview有刷新控件，所以头布局的位置是1，即第二个
				if (firstVisibleItem == 0)
				{
					// 获取头布局
					View v = listview.getChildAt(0);
					if (v != null)
					{
						// 获取头布局现在的最上部的位置的相反数
						int top = -v.getTop();
						System.out.println("=====top=====" + top);
						// 获取头布局的高度
						headerHeight = v.getHeight();
						// 满足这个条件的时候，是头布局Listview的最上面第一个控件的时候，只有这个时候，我们才调整透明度
						if (top <= headerHeight && top >= 0)
						{
							// 获取当前位置占头布局高度的百分比
							float f = (float) (top + rl_title.getHeight()) / (float) (headerHeight);
							System.out.println("=======gggg=====" + (int) (f * 255));
							if (top == 0)
							{
								rl_title.getBackground().setAlpha(0);
							} else
							{
								if ((int) (f * 255) <= 255)
									rl_title.getBackground().setAlpha((int) (f * 255));
								else
									rl_title.getBackground().setAlpha(255);
							}
							// 通知标题栏刷新显示
							rl_title.invalidate();
						}
					}
				} else if (firstVisibleItem > 0)
				{
					rl_title.getBackground().setAlpha(255);
				} else
				{
					rl_title.getBackground().setAlpha(0);
				}
			}
		});

	}

	// 自定义适配器
	private class MyAdapter extends BaseAdapter
	{

		// 默认显示10个item
		@Override
		public int getCount()
		{
			return 10;
		}

		@Override
		public Object getItem(int position)
		{
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = View.inflate(MainActivity.this, R.layout.ietm_list, null);
			}

			return convertView;
		}

	}

}
