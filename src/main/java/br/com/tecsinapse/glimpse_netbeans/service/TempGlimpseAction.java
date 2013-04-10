package br.com.tecsinapse.glimpse_netbeans.service;

import br.com.tecsinapse.glimpse_netbeans.editor.ConnectionAction;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.AbstractAction;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

public class TempGlimpseAction extends AbstractAction {

    private static final String DIR_GLIMPSE_COMMANDS = "Glimpse/Commands";
    private final GlimpseConnector connector;

    public TempGlimpseAction(GlimpseConnector connector) {
        super(NbBundle.getMessage(TempGlimpseAction.class,
                "TempGlimpseAction.executeTemp"));
        this.connector = connector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileObject glimpseDir = getGlimpseDir();

        try {
            String fileName = FileUtil.findFreeFileName(glimpseDir, "Glimpse", "groovy");
            FileObject tempFile = glimpseDir.createData(fileName, "groovy");

            DataObject dobj = DataObject.find(tempFile);

            OpenCookie openCookie = dobj.getLookup().lookup(OpenCookie.class);
            openCookie.open();

            EditorCookie editor = dobj.getLookup().lookup(EditorCookie.class);
            editor.getDocument().putProperty(ConnectionAction.CONNECTOR_NAME, connector.getName());
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static FileObject getGlimpseDir() {
        FileObject configDir = FileUtil.getConfigFile(DIR_GLIMPSE_COMMANDS);
        if (configDir == null) {
            try {
                configDir = FileUtil.createFolder(FileUtil.getConfigRoot(), DIR_GLIMPSE_COMMANDS);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return configDir;
    }
}
