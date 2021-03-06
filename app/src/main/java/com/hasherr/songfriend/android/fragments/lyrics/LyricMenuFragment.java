package com.hasherr.songfriend.android.fragments.lyrics;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.ui.adapter.CustomDragArrayAdapter;
import com.hasherr.songfriend.android.utility.FileUtilities;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;

/**
 * Created by Evan on 1/18/2016.
 */
public class LyricMenuFragment extends Fragment
{
    private DynamicListView dynamicListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_lyric, container, false);
        dynamicListView = (DynamicListView) view.findViewById(R.id.lyricSectionDragView);
        ArrayList<String> b = new ArrayList<>();
        b.add("Introduction");
        b.add("Chorus");
        b.add("Verse");
        b.add("Chorus");
        b.add("Bridge");
        b.add("Verse");
        b.add("Chorus");
        b.add("Solo");
        b.add("Chorus");
        CustomDragArrayAdapter a = new CustomDragArrayAdapter(getActivity(), R.layout.drag_drop_row, R.id.list_row_draganddrop_textview, b);
        SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(a);
        animationAdapter.setAbsListView(dynamicListView);
        dynamicListView.setAdapter(animationAdapter);
        dynamicListView.enableDragAndDrop();
        dynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        dynamicListView.startDragging(position);
                        return true;
                    }
                });
        return view;
    }

    private void initAddLyricSection(View view)
    {
        final Button addLyricSectionButton = (Button) view.findViewById(R.id.addLyricSectionButton);
        addLyricSectionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddLyricSectionDialogFragment addLyricSectionDialogFragment = new AddLyricSectionDialogFragment();
                addLyricSectionDialogFragment.setTargetFragment(LyricMenuFragment.this, FileUtilities.MODIFY_REQUEST_CODE);
                addLyricSectionDialogFragment.show(getFragmentManager(), "add_lyric_section_dialog_fragment");
            }
        });
    }
}

