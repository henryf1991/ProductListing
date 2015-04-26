package com.example.mcafee.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mcafee.DetailsActivity;
import com.example.mcafee.R;
import com.example.mcafee.model.Product;
import com.example.mcafee.utils.DiskBitmapCache;
import com.example.mcafee.utils.Util;

public class ProductListAdapter extends BaseAdapter{

	static final String TAG = "McAfeeTest";
	
	Context context;
	LayoutInflater inflater;
	List<Product> product_list;
	ImageLoader imageLoader;
	int max_cache_size = 1000000;
	
	public ProductListAdapter(Context context, List<Product> product_list,RequestQueue volleyQueue) {
		this.context = context;
		this.product_list = product_list;
		this.inflater = LayoutInflater.from(context);
		imageLoader = new ImageLoader(volleyQueue, new DiskBitmapCache(context.getCacheDir(),max_cache_size));
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return product_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return product_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	public void setProductList(ArrayList<Product> product_list) {
		this.product_list = product_list;
		notifyDataSetChanged();
	}
	
	public List<Product> getProductList() {
		return this.product_list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_product, null);
            holder = new ViewHolder();
            holder.image = (NetworkImageView) convertView.findViewById(R.id.prodImg);
            holder.title = (TextView) convertView.findViewById(R.id.prodName);
            holder.rating = (RatingBar) convertView.findViewById(R.id.prodRating);
            holder.download = (ImageView) convertView.findViewById(R.id.action_download);
            holder.detail = (ImageView) convertView.findViewById(R.id.action_detail);
            holder.share = (ImageView) convertView.findViewById(R.id.action_share);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.title.setText(product_list.get(position).getName());
        holder.rating.setRating(Float.parseFloat(product_list.get(position).getRating()));
        Log.d(TAG, product_list.get(position).getImage());
        holder.image.setImageUrl(product_list.get(position).getImage(), imageLoader);
        
        holder.download.setOnClickListener(actionbuttonclickListener);
        holder.detail.setOnClickListener(actionbuttonclickListener);
        holder.share.setOnClickListener(actionbuttonclickListener);
        
        holder.download.setTag(product_list.get(position));
        holder.detail.setTag(product_list.get(position));
        holder.share.setTag(product_list.get(position));
        
        return convertView;
    }
    
    class ViewHolder {
        TextView title;
        RatingBar rating;
        NetworkImageView image;
        ImageView download, detail, share;
    }
    
    OnClickListener actionbuttonclickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.action_download:
				String appPackageName = ((Product) v.getTag()).getUrl().split("=")[1];
				try {
				    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
				} catch (android.content.ActivityNotFoundException anfe) {
				    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
				}
				break;
			case R.id.action_detail:
				Intent i = new Intent(context, DetailsActivity.class);
				i.putExtra("data", Util.objToJson(((Product) v.getTag())));
				context.startActivity(i);
				break;
			case R.id.action_share:
				String message = "Name : "+((Product) v.getTag()).getName()+" \n link : "+((Product) v.getTag()).getUrl();
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, message);
				sendIntent.setType("text/plain");
				context.startActivity(sendIntent);
				break;
			default:
				break;
			}
			
		}
	};
}

