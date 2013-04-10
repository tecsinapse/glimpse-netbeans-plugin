package br.com.tecsinapse.glimpse_netbeans.service;

import br.com.tecsinapse.glimpse_netbeans.gui.GlimpseManagerPanelDescriptor;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public class ServiceNode extends AbstractNode {

    private final GlimpseConnector connector;

    public ServiceNode(GlimpseConnector connector, Lookup lookup) {
        super(Children.LEAF, lookup);
        this.connector = connector;
    }

    @Override
    public String getDisplayName() {
        return connector.getName();
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new TempGlimpseAction(connector), new EditAction(), new RemoveAction()};
    }

    private class EditAction extends AbstractAction {

        public EditAction() {
            super(NbBundle.getMessage(ServiceNode.class,
                    "ServiceNode.edit"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GlimpseManagerPanelDescriptor.edit(connector);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class RemoveAction extends AbstractAction {

        public RemoveAction() {
            super(NbBundle.getMessage(ServiceNode.class,
                    "ServiceNode.remove"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ServiceRegistry.getInstance().removeService(connector);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
