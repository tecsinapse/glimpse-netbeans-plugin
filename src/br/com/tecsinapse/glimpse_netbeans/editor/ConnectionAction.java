package br.com.tecsinapse.glimpse_netbeans.editor;

import br.com.tecsinapse.glimpse_netbeans.service.TempGlimpseAction;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.DataEditorSupport;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

@ActionID(category = "Editor", id = "br.com.tecsinapse.glimpse_netbeans.editor.ConnectionAction")
@ActionRegistration(displayName = "Connection", lazy = false)
@ActionReference(path = "Editors/text/x-groovy/Toolbars/Default", position = -200)
public class ConnectionAction extends AbstractAction implements ContextAwareAction {

    public static final String CONNECTOR_NAME = "br.com.tecsinapse.glimse.connector";

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        EditorCookie context = actionContext.lookup(EditorCookie.class);
        closeListeners(context);

        return new ConnectionPanel.ConnectionToolbar(actionContext);
    }

    private void closeListeners(EditorCookie context) {
        FileObject tempDir = TempGlimpseAction.getGlimpseDir();
        final DataObject dobj = (DataObject) context.getDocument().getProperty(
                Document.StreamDescriptionProperty);

        if (FileUtil.isParentOf(tempDir, dobj.getPrimaryFile())) {
            DataEditorSupport lookup = dobj.getLookup().lookup(DataEditorSupport.class);
            lookup.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (EditorCookie.Observable.PROP_DOCUMENT.equals(evt.getPropertyName())
                            && (evt.getOldValue() instanceof StyledDocument)
                            && evt.getNewValue() == null) {
                        try {
                            dobj.getPrimaryFile().delete();
                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            });
        }
    }
}
