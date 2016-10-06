package thinktechsol.msquare;

import android.os.Environment;

import java.io.File;

import thinktechsol.msquare.activities.AlbumStorageDirFactory;

/**
 * Created by LENOVO on 10/5/2016.
 */

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

    // Standard storage location for digital camera files
    private static final String CAMERA_DIR = "/dcim/";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }
}
