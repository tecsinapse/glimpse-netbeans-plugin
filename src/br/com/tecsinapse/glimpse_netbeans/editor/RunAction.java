package br.com.tecsinapse.glimpse_netbeans.editor;

import static br.com.tecsinapse.glimpse_netbeans.editor.ConnectionAction.CONNECTOR_NAME;
import br.com.tecsinapse.glimpse_netbeans.service.GlimpseExecution;
import br.com.tecsinapse.glimpse_netbeans.service.ServiceRegistry;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

@ActionID(category = "Editor", id = "br.com.tecsinapse.glimpse_netbeans.editor.RunAction")
@ActionRegistration(displayName = "#CTL_Run", lazy = true, iconBase = "br/com/tecsinapse/glimpse_netbeans/icons/run.png")
@ActionReference(path = "Editors/text/x-groovy/Toolbars/Default", position = -100)
@NbBundle.Messages("CTL_Run=Run")
public class RunAction extends AbstractAction {

    private final EditorCookie context;

    public RunAction(EditorCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (context.isModified()) {
            try {
                context.saveDocument();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        Object property = context.getDocument().getProperty(CONNECTOR_NAME);
        if (property == null) {
            DialogDisplayer.getDefault().notify(
                    new NotifyDescriptor.Message("There is no connection selected",
                    NotifyDescriptor.ERROR_MESSAGE));
            return;
        }

        String prop = String.valueOf(context.getDocument().getProperty(Document.TitleProperty));
        final String title = prop.substring(prop.lastIndexOf(File.separatorChar) + 1);
        final String connectoName = (String) property;
        try {
            GlimpseExecution glimpseExecution = new GlimpseExecution(
                    ServiceRegistry.getInstance().findService(connectoName), title,
                    context.getDocument().getText(0, context.getDocument().getLength()));
            glimpseExecution.execute();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
