package br.com.tecsinapse.glimpse_netbeans;

import br.com.tecsinapse.glimpse_netbeans.service.TempGlimpseAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.ModuleInstall;
import org.openide.text.DataEditorSupport;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        TopComponent.Registry registry = TopComponent.getRegistry();
        registry.addPropertyChangeListener(new RemoveFileListener());
    }

    private static class RemoveFileListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(TopComponent.Registry.PROP_TC_CLOSED)
                    && evt.getNewValue() instanceof TopComponent) {
                TopComponent tp = (TopComponent) evt.getNewValue();
                DataEditorSupport data = tp.getLookup().lookup(DataEditorSupport.class);
                if (data != null && data.getDataObject() != null
                        && data.getDataObject().getPrimaryFile() != null) {
                    FileObject tempDir = TempGlimpseAction.getGlimpseDir();
                    FileObject primaryFile = data.getDataObject().getPrimaryFile();
                    if (FileUtil.isParentOf(tempDir, primaryFile)) {
                        try {
                            primaryFile.delete();
                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            }
        }
    }
}
