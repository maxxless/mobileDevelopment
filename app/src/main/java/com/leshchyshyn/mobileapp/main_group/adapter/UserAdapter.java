package com.leshchyshyn.mobileapp.main_group.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.data.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> list) {
        this.userList = list;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.firstNameTv.setText(user.getFirstName());
        holder.lastNameTv.setText(user.getLastName());
        holder.emailTv.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateUsers(final List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView firstNameTv;
        private TextView lastNameTv;
        private TextView emailTv;

        public ViewHolder(View itemView) {
            super(itemView);
            firstNameTv = itemView.findViewById(R.id.row_user_firstName_tv);
            lastNameTv = itemView.findViewById(R.id.row_user_lastName_tv);
            emailTv = itemView.findViewById(R.id.row_user_email_tv);
        }
    }
}
