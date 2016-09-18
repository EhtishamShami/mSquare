package thinktechsol.msquare.activities.buyer;


import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class FriendListFragment extends ListFragment {
	
	private static final int REQUEST_CODE_ADD_FRIEND = 1;
	
	public static FriendListFragment newInstance() {
		return new FriendListFragment();
	}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

	}
}
