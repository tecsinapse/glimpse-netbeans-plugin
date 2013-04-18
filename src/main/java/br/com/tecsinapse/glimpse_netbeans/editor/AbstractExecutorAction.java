package br.com.tecsinapse.glimpse_netbeans.editor;

import static br.com.tecsinapse.glimpse_netbeans.editor.ConnectionAction.CONNECTOR_NAME;
import br.com.tecsinapse.glimpse_netbeans.service.GlimpseExecution;
import br.com.tecsinapse.glimpse_netbeans.service.ServiceRegistry;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.text.Document;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;

public abstract class AbstractExecutorAction extends AbstractAction {

    private final EditorCookie context;

    public AbstractExecutorAction(EditorCookie context) {
        this.context = context;
    }

    protected EditorCookie getContext() {
        return context;
    }

    protected final void execute(String execute) {
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

        GlimpseExecution glimpseExecution = new GlimpseExecution(
                ServiceRegistry.getInstance().findService(connectoName),
                title, execute);
        glimpseExecution.execute();
    }
}
