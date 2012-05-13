package gr.aueb.mscis.Activities;

//yooo
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class ChordDroidActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Photos
        TabSpec nodesspec = tabHost.newTabSpec("NodesIn");
        // setting Title and Icon for the Tab
        nodesspec.setIndicator("NodesIn", getResources().getDrawable(R.drawable.icon));
        Intent photosIntent = new Intent(this, ContentNodesActivity.class);
        nodesspec.setContent(photosIntent);
 
        // Tab for Songs
        TabSpec dhtspec = tabHost.newTabSpec("DHT");
        dhtspec.setIndicator("DHT", getResources().getDrawable(R.drawable.icon));
        Intent songsIntent = new Intent(this,DHTActivity.class);
        dhtspec.setContent(songsIntent);
 
        // Tab for Videos
        TabSpec filesspec = tabHost.newTabSpec("Files");
        filesspec.setIndicator("Files", getResources().getDrawable(R.drawable.icon));
        Intent videosIntent = new Intent(this, FilesActivity.class);
        filesspec.setContent(videosIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(nodesspec); 
        tabHost.addTab(dhtspec); 
        tabHost.addTab(filesspec); 
        
        tabHost.setCurrentTab(0);
    }
}