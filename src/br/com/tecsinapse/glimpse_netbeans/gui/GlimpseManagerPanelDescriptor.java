package br.com.tecsinapse.glimpse_netbeans.gui;

import br.com.tecsinapse.glimpse_netbeans.service.GlimpseConnector;
import br.com.tecsinapse.glimpse_netbeans.service.ServiceRegistry;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import javax.swing.event.ChangeListener;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class GlimpseManagerPanelDescriptor implements WizardDescriptor.Panel<WizardDescriptor> {

    public static void edit(GlimpseConnector service) throws IOException {
        final GlimpseManagerPanelDescriptor descriptor = new GlimpseManagerPanelDescriptor(service);
        if (show(descriptor)) {
            ServiceRegistry.getInstance().refreshService(descriptor.updateOrCreateService());
        }
    }

    public static void create() throws IOException {
        final GlimpseManagerPanelDescriptor descriptor = new GlimpseManagerPanelDescriptor(null);
        if (show(descriptor)) {
            ServiceRegistry.getInstance().addService(descriptor.updateOrCreateService());
        }
    }

    private static boolean show(GlimpseManagerPanelDescriptor descriptor) {
        @SuppressWarnings("unchecked")
        WizardDescriptor.Panel<WizardDescriptor>[] wizardPanels =
                (WizardDescriptor.Panel<WizardDescriptor>[]) Array.newInstance(WizardDescriptor.Panel.class, 1);
        wizardPanels[0] = descriptor;
        WizardDescriptor wizardDescriptor = new WizardDescriptor(wizardPanels);
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Glimpse Settings");

        return DialogDisplayer.getDefault().notify(wizardDescriptor) == WizardDescriptor.FINISH_OPTION;
    }
    private final ChangeSupport changeSupport = new ChangeSupport(this);
    private final GlimpseConnector service;
    private final GlimpseManagerPanel component;
    private WizardDescriptor wizardDescriptor;

    public GlimpseManagerPanelDescriptor(GlimpseConnector service) {
        this.service = service;
        component = new GlimpseManagerPanel(this);

        component.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, 0);
        component.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, new String[]{component.getName()});
        component.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
        component.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
        component.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
    }

    private GlimpseConnector updateOrCreateService() {
        return new GlimpseConnector(component.getNameTextField().getText(),
                component.getUrlTextField().getText(),
                component.getUsernameTextField().getText(),
                new String(component.getPasswordTextField().getPassword()));
    }

    @Override
    public GlimpseManagerPanel getComponent() {
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        if (component.getNameTextField().getText().isEmpty()) {
            addErro("GlimpseManagerPanel.name.valid");
            return false;
        }
        if (service == null && ServiceRegistry.getInstance().exists(component.getNameTextField().getText())) {
            addErro("GlimpseManagerPanel.name.duplicated");
            return false;
        }

        if (component.getUrlTextField().getText().isEmpty()) {
            addErro("GlimpseManagerPanel.url.valid");
            return false;
        }

        if (component.getPasswordTextField().getPassword().length != 0
                && component.getUsernameTextField().getText().isEmpty()) {
            addErro("GlimpseManagerPanel.password.valid");
            return false;
        }
        wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, null);
        return true;
    }

    void addErro(String keyMessageError) {
        wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE,
                NbBundle.getMessage(GlimpseManagerPanel.class, keyMessageError));
    }

    void fireChange() {
        changeSupport.fireChange();
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        wizardDescriptor = wiz;
        if (service != null) {
            component.getNameTextField().setText(service.getName());
            component.getUrlTextField().setText(service.getUrl());
            component.getUsernameTextField().setText(service.getUsername());
            component.getPasswordTextField().setText(service.getPassword());
        } else {
            component.getNameTextField().setEditable(true);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
    }
}
