package com.oscardevelop.simpletodo;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import android.content.*;
import android.net.Uri;
import android.app.*;
import android.os.*;
import android.widget.RelativeLayout;
import android.graphics.*;
import android.content.*;
import android.content.res.AssetManager;
import android.support.v7.widget.PopupMenu;


public class Adapter extends RecyclerView.Adapter<Adapter.UserViewHolder> {
    private List<Db> listUsers;
	private Context ctx;
	private TodoData databaseHelper;
	private Adapter adap;

	private Db user;
	
	
    public Adapter(Context ctx, List<Db> listUsers) {
        this.listUsers = listUsers;
		this.ctx = ctx;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        holder.textTaskName.setText(listUsers.get(position).getTaskName());
        holder.textDate.setText(listUsers.get(position).getDate());
	
		

    }

    @Override
    public int getItemCount() {

		return listUsers.size();
    }
	

	
    /**
     * ViewHolder class
     */
	public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textTaskName;
        public TextView textDate;
        

        public UserViewHolder(View view) {
            super(view);
            textTaskName = (TextView) view.findViewById(R.id.title);
            textDate = (TextView) view.findViewById(R.id.sdetails);
			
		}
    }
}
