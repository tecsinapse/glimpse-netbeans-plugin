package br.com.tecsinapse.glimpse_netbeans.editor;

import br.com.tecsinapse.glimpse_netbeans.service.GlimpseConnector;
import br.com.tecsinapse.glimpse_netbeans.service.GlimpseLookup;
import br.com.tecsinapse.glimpse_netbeans.service.ServiceRegistry;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LayoutStyle;
import javax.swing.text.StyledDocument;
import org.openide.awt.Mnemonics;
import org.openide.cookies.EditorCookie;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

public class ConnectionPanel extends javax.swing.JPanel {

    static class ConnectionToolbar extends AbstractAction implements Presenter.Toolbar {

        private final Lookup lookup;

        ConnectionToolbar(Lookup lookup) {
            this.lookup = lookup;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }

        @Override
        public Component getToolbarPresenter() {
            return new ConnectionPanel(lookup);
        }
    }

    private static final class ComboModel extends AbstractListModel<GlimpseConnector> implements
            ComboBoxModel<GlimpseConnector> {

        private GlimpseConnector connector;
        private final List<GlimpseConnector> connectors;
        private final Lookup.Result<GlimpseConnector> connectorsResult;

        private ComboModel() {
            ServiceRegistry.getInstance();
            this.connectors = new ArrayList<GlimpseConnector>();
            refreshConnectors();
            connectorsResult = GlimpseLookup.getDefault().lookupResult(GlimpseConnector.class);
            connectorsResult.addLookupListener(new LookupListener() {
                @Override
                public void resultChanged(LookupEvent ev) {
                    refreshConnectors();
                }
            });
        }

        private void refreshConnectors() {
            Collection<? extends GlimpseConnector> lookupAll =
                    GlimpseLookup.getDefault().lookupAll(GlimpseConnector.class);
            connectors.clear();
            connectors.addAll(lookupAll);
            Collections.sort(connectors);
            if (connector != null && !connectors.contains(connector)) {
                setSelectedItem(null);
            }
            fireContentsChanged(this, 0, connectors.size());
        }

        @Override
        public int getSize() {
            return connectors.size();
        }

        @Override
        public GlimpseConnector getElementAt(int index) {
            return connectors.get(index);
        }

        @Override
        public Object getSelectedItem() {
            return connector;
        }

        @Override
        public void setSelectedItem(Object anItem) {
            connector = (GlimpseConnector) anItem;
        }

        private void setSelectedConnector(String connectorName) {
            for (GlimpseConnector glimpseConnector : connectors) {
                if (glimpseConnector.getName().equals(connectorName)) {
                    setSelectedItem(glimpseConnector);
                    break;
                }
            }
        }
    }
    private final Lookup lookup;
    private final ComboModel model;

    private ConnectionPanel(Lookup lookup) {
        initComponents();
        model = new ComboModel();
        this.lookup = lookup;

        EditorCookie context = lookup.lookup(EditorCookie.class);
        String connector = (String) context.getDocument().getProperty(
                ConnectionAction.CONNECTOR_NAME);
        model.setSelectedConnector(connector);

        jComboBox1.setModel(model);
        jComboBox1.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Object nValue = value;
                if (value instanceof GlimpseConnector) {
                    nValue = ((GlimpseConnector) value).getName();
                }
                return super.getListCellRendererComponent(list, nValue, index,
                        isSelected, cellHasFocus);
            }
        });
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox source = (JComboBox) e.getSource();

                EditorCookie context = ConnectionPanel.this.lookup.lookup(EditorCookie.class);
                String connectorName = null;
                if (source.getSelectedItem() instanceof GlimpseConnector) {
                    connectorName = ((GlimpseConnector) source.getSelectedItem()).getName();
                }
                final StyledDocument document = context.getDocument();
                document.putProperty(ConnectionAction.CONNECTOR_NAME, connectorName);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        jComboBox1 = new JComboBox();

        Mnemonics.setLocalizedText(jLabel1, NbBundle.getMessage(ConnectionPanel.class, "ConnectionPanel.jLabel1.text")); // NOI18N

        jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox jComboBox1;
    private JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
