package com.zhao.myreader.ui.home.bookcase;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.zhao.myreader.R;
import com.zhao.myreader.creator.DialogCreator;
import com.zhao.myreader.greendao.entity.Book;
import com.zhao.myreader.greendao.service.BookService;
import com.zhao.myreader.util.DipPxUtil;
import com.zhao.myreader.util.StringHelper;

import java.util.ArrayList;

/**
 * Created by zhao on 2017/7/26.
 */

public class BookcaseAdapter extends ArrayAdapter<Book> {

    private int mResourceId;
    private BookService mBookService;


    public BookcaseAdapter(Context context, int resourceId, ArrayList<Book> datas) {
        super(context, resourceId, datas);
        mResourceId = resourceId;
        mBookService = new BookService();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(mResourceId, null);
            viewHolder.ivBookImg =  convertView.findViewById(R.id.iv_book_img);
            viewHolder.tvBookName =  convertView.findViewById(R.id.tv_book_name);
            viewHolder.tvNoReadNum =  convertView.findViewById(R.id.tv_no_read_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initView(position, viewHolder);
        return convertView;
    }

    private void initView(int postion, ViewHolder viewHolder) {
        final Book book = getItem(postion);
        if (StringHelper.isEmpty(book.getImgUrl())) {
            book.setImgUrl("");
        }
        Glide.with(getContext())
                .load(book.getImgUrl())
//                .override(DipPxUtil.dip2px(getContext(), 80), DipPxUtil.dip2px(getContext(), 120))
                .error(R.mipmap.no_image)
                .placeholder(R.mipmap.no_image)
                .into(viewHolder.ivBookImg);
        viewHolder.tvBookName.setText(book.getName());
        if (book.getNoReadNum() != 0) {
            viewHolder.tvNoReadNum.setVisibility(View.VISIBLE);
            if (book.getNoReadNum() > 99) {
                viewHolder.tvNoReadNum.setText("...");
            } else {
                viewHolder.tvNoReadNum.setText(String.valueOf(book.getNoReadNum()));
            }

        } else {
            viewHolder.tvNoReadNum.setVisibility(View.GONE);
        }

    }

    class ViewHolder {
        ImageView ivBookImg;
        TextView tvBookName;
        TextView tvNoReadNum;
    }

}
