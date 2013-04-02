package br.com.tecsinapse.glimpse_netbeans.service;

import br.com.tecsinapse.glimpse_netbeans.gui.GlimpseManagerPanelDescriptor;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

public class RootNode extends AbstractNode {

    private static RootNode node;
    @StaticResource
    public static final String ICON = "br/com/tecsinapse/glimpse_netbeans/icons/glimpse-icon.png";
    public static final String DISPLAY_NAME = "Glimpse";

    public RootNode(Children children) {
        super(children);
        setDisplayName(DISPLAY_NAME);
        setIconBaseWithExtension(ICON);
    }

    @ServicesTabNodeRegistration(displayName = RootNode.DISPLAY_NAME,
            name = "glimpse-service", iconResource = RootNode.ICON)
    public static synchronized RootNode getInstance() throws IOException {
        if (node == null) {
            node = new RootNode(Children.create(ServiceRegistry.getInstance(), true));
        }
        return node;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new AddAction()};
    }

    private class AddAction extends AbstractAction {

        public AddAction() {
            super(NbBundle.getMessage(ServiceNode.class,
                    "ServiceNode.add"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GlimpseManagerPanelDescriptor.create();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
