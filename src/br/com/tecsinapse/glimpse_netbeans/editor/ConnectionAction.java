package br.com.tecsinapse.glimpse_netbeans.editor;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ContextAwareAction;
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
        return new ConnectionPanel.ConnectionToolbar(actionContext);
    }
}
