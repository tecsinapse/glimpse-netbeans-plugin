package br.com.tecsinapse.glimpse_netbeans.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;

public class GlimpseManagerPanel extends JPanel {

    private final GlimpseManagerPanelDescriptor descriptor;

    GlimpseManagerPanel(GlimpseManagerPanelDescriptor descriptor) {
        this.descriptor = descriptor;
        initComponents();
    }

    @Override
    public String getName() {
        return "Glimpse Service";
    }

    JTextField getNameTextField() {
        return name;
    }

    JTextField getUrlTextField() {
        return url;
    }

    JTextField getUsernameTextField() {
        return username;
    }

    JPasswordField getPasswordTextField() {
        return password;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        name = new JTextField();
        url = new JTextField();
        username = new JTextField();
        password = new JPasswordField();

        Mnemonics.setLocalizedText(jLabel1, NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.jLabel1.text")); // NOI18N

        Mnemonics.setLocalizedText(jLabel2, NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.jLabel2.text")); // NOI18N

        Mnemonics.setLocalizedText(jLabel3, NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.jLabel3.text")); // NOI18N

        Mnemonics.setLocalizedText(jLabel4, NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.jLabel4.text")); // NOI18N

        name.setEditable(false);
        name.setColumns(20);
        name.setText(NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.name.text")); // NOI18N
        name.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                propChanged(evt);
            }
        });

        url.setColumns(20);
        url.setText(NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.url.text")); // NOI18N
        url.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                propChanged(evt);
            }
        });

        username.setColumns(20);
        username.setText(NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.username.text")); // NOI18N
        username.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                propChanged(evt);
            }
        });

        password.setColumns(20);
        password.setText(NbBundle.getMessage(GlimpseManagerPanel.class, "GlimpseManagerPanel.password.text")); // NOI18N
        password.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                propChanged(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(url, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(url, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void propChanged(KeyEvent evt) {//GEN-FIRST:event_propChanged
        descriptor.fireChange();
    }//GEN-LAST:event_propChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField name;
    private JPasswordField password;
    private JTextField url;
    private JTextField username;
    // End of variables declaration//GEN-END:variables
}
