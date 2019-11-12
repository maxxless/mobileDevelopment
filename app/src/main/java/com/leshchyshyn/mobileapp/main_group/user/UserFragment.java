package com.leshchyshyn.mobileapp.main_group.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leshchyshyn.mobileapp.R;

import java.util.Objects;

public class UserFragment extends Fragment implements IUserView, View.OnClickListener {

    private View view;
    private Context mContext;

    private RecyclerView recyclerView;

    private FloatingActionButton searchFab;
    private SwipeRefreshLayout swipeRefreshLayout;

    private UserPresenter userPresenter;

    @Override
    public void onDetach() {
        super.onDetach();
        this.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);
        mContext = view.getContext();

        initView();
        initListener();
        initPresenter();

        userPresenter.loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userPresenter.loadData();
            }
        });

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.user_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        searchFab = view.findViewById(R.id.user_search_fab);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshUsers);
    }

    private void initListener() {
        searchFab.setOnClickListener(this);
    }

    private void initPresenter() {
        userPresenter = new UserPresenter(this, mContext);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_search_fab: {
                final EditText idEdit = new EditText(mContext);
                idEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle(R.string.searchUser)
                        .setMessage(R.string.searchUserMessage)
                        .setView(idEdit)
                        .setPositiveButton(R.string.searchLabel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = idEdit.getText().toString();
                                userPresenter.searchUserByName(id);
                            }
                        })
                        .setNegativeButton(R.string.close, null)
                        .create();
                dialog.show();
                break;
            }
        }
    }

    private void showProgressLoaderWithBackground(boolean visibility, String text) {
        if (text == null) {
            text = "";
        }

        ((TextView) view.findViewById(R.id.progress_bar_text)).setText(text);

        if (visibility) {
            view.findViewById(R.id.container_progress_bar).setVisibility(View.VISIBLE);
            Objects.requireNonNull(getActivity()).getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            view.findViewById(R.id.container_progress_bar).setVisibility(View.GONE);
            Objects.requireNonNull(getActivity()).getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, mContext.getString(R.string.loadData));
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, mContext.getString(R.string.loadData));
    }

    @Override
    public void hideRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNotFound() {
        Toast.makeText(mContext, R.string.notFound, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotInternetConnection() {
        Toast.makeText(mContext, R.string.noInternet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setEnabledSearch(boolean enabled) {
        searchFab.setEnabled(enabled);
    }
}
