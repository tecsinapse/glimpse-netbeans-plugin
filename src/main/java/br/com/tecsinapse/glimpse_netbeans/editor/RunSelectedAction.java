package br.com.tecsinapse.glimpse_netbeans.editor;

import java.awt.event.ActionEvent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.util.NbBundle;

@ActionID(category = "Editor", id = "br.com.tecsinapse.glimpse_netbeans.editor.RunSelectedAction")
@ActionRegistration(displayName = "#CTL_RunSelected", lazy = true,
        iconBase = "br/com/tecsinapse/glimpse_netbeans/icons/run-selected.png")
@ActionReferences({
    @ActionReference(path = "Editors/text/x-groovy/Toolbars/Default", position = -99),
    @ActionReference(path = "Shortcuts", name = "AS-E")
})
@NbBundle.Messages("CTL_RunSelected=Run Selected")
public class RunSelectedAction extends AbstractExecutorAction {

    public RunSelectedAction(EditorCookie context) {
        super(context);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String selectedText = getContext().getOpenedPanes()[0].getSelectedText();
        if (selectedText == null) {
            DialogDisplayer.getDefault().notify(
                    new NotifyDescriptor.Message("Select a block to execute",
                    NotifyDescriptor.ERROR_MESSAGE));
            return;
        }
        execute(selectedText);
    }
}
