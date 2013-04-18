package br.com.tecsinapse.glimpse_netbeans.editor;

import java.awt.event.ActionEvent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.util.NbBundle;

@ActionID(category = "Editor", id = "br.com.tecsinapse.glimpse_netbeans.editor.RunAction")
@ActionRegistration(displayName = "#CTL_Run", lazy = true,
        iconBase = "br/com/tecsinapse/glimpse_netbeans/icons/run.png")
@ActionReferences({
    @ActionReference(path = "Editors/text/x-groovy/Toolbars/Default", position = -100),
    @ActionReference(path = "Shortcuts", name = "AS-W")
})
@NbBundle.Messages("CTL_Run=Run")
public class RunAction extends AbstractExecutorAction {

    public RunAction(EditorCookie context) {
        super(context);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute(getContext().getOpenedPanes()[0].getText());
    }
}
