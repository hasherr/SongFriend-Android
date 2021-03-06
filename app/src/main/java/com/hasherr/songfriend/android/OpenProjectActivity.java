package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hasherr.songfriend.android.custom.ToolBarActivity;
import com.hasherr.songfriend.android.ui.handler.DeleteDialogHandler;
import com.hasherr.songfriend.android.ui.handler.ListHandler;
import com.hasherr.songfriend.android.ui.listener.FloatingActionButtonListener;
import com.hasherr.songfriend.android.utility.FileUtilities;

public class OpenProjectActivity extends ToolBarActivity implements FloatingActionButtonListener, ListHandler.ListHandlerListener
{
    private ListHandler listHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_project);
        initToolbar(R.id.openProjectToolbar, "Open a project");
        initFloatingActionButton();
        initListHandler();
    }

    @Override
    protected void initValues()
    {
        initPath();
    }

    @Override
    public void initPath()
    {
        path = FileUtilities.PROJECT_DIRECTORY;
    }

    @Override
    public void initFloatingActionButton()
    {
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(
                R.id.projectFloatingActionButton);
        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.logoGreen));
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, FileUtilities.PROJECT_DIRECTORY);
                startActivity(intentManager.createIntent(OpenProjectActivity.this, NewProjectActivity.class));
            }
        });
    }

    @Override
    public void onResume()
    {
        listHandler.refresh(FileUtilities.getDirectoryList(path));
        super.onResume();
    }

    @Override
    public void initListHandler()
    {
        listHandler = new ListHandler((ListView) findViewById(R.id.projectListView),
                new ArrayAdapter<>(this, R.layout.row, FileUtilities.getDirectoryList(path)));

        listHandler.initListViewItemClick(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String elementName = ((String) parent.getItemAtPosition(position));
                intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, path + "/" + elementName);
                startActivity(intentManager.createIntent(OpenProjectActivity.this, OpenDraftActivity.class));
            }
        });

        listHandler.initListViewLongItemClick(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final String itemToDelete = (String) parent.getItemAtPosition(position);
                DeleteDialogHandler deleteDialogHandler = new DeleteDialogHandler();
                deleteDialogHandler.initialize(OpenProjectActivity.this, "Delete Project",
                        "Are you sure you want to delete this project?", path + "/" + itemToDelete, path, true, listHandler);
                deleteDialogHandler.show();
                return true;
            }
        });
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus)
//    {
//        super.onWindowFocusChanged(hasFocus);
//        WindowSlideInAnimator windowAnimator = new WindowSlideInAnimator();
//        ViewGroup windowRoot = (ViewGroup) findViewById(android.R.id.content);
//        windowAnimator.animate(windowRoot, hasFocus, WindowSlideInAnimator.SLIDE_LEFT);
//    }
}
