package br.com.tecsinapse.glimpse_netbeans.service;

import br.com.tecsinapse.glimpse.client.ClientPoll;
import br.com.tecsinapse.glimpse.client.Monitor;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.AbstractAction;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

public class GlimpseExecution {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final GlimpseConnector connector;
    private final String titleTab;
    private final String script;

    public GlimpseExecution(GlimpseConnector connector, String titleTab, String script) {
        this.connector = connector;
        this.titleTab = titleTab;
        this.script = script;
    }

    public void execute() {
        connector.getExecutor().submit(new Runnable() {
            @Override
            public void run() {

                final InputOutput io = IOProvider.getDefault().getIO(titleTab, true);
                io.select();
                io.getOut().append("Executing: ").append(titleTab).append(LINE_SEPARATOR);
                MonitorImpl monitorImpl = new MonitorImpl(connector, io, titleTab, script);
                try {
                    monitorImpl.start();
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
    }

    static class MonitorImpl implements Monitor {

        private final OutputWriter out;
        private final GlimpseConnector connector;
        private final InputOutput io;
        private final ProgressHandle handle;
        private final AtomicBoolean canceled;
        private final String script;
        private String id;
        private int work = 0;
        private int worked = 0;

        public MonitorImpl(GlimpseConnector connector,
                InputOutput io, String titleTab, String script) {
            out = io.getOut();
            this.connector = connector;
            this.io = io;
            this.canceled = new AtomicBoolean();
            this.script = script;

            handle = ProgressHandleFactory.createHandle(titleTab,
                    new Cancellable() {
                        @Override
                        public boolean cancel() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //outra thread httpConnector.cancel em alguns casos não respondeu
                                    MonitorImpl.this.connector.getHttpConnector().cancel(id);
                                    MonitorImpl.this.canceled.set(true);
                                }
                            }).start();
                            return true;
                        }
                    }, new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            MonitorImpl.this.io.select();
                        }
                    });
        }

        private void start() throws InterruptedException {
            id = connector.getHttpConnector().start(script);
            handle.start();

            try {
                while (!canceled.get()) {
                    List<ClientPoll> poll = connector.getHttpConnector().poll(id);
                    for (ClientPoll clientPoll : poll) {
                        clientPoll.apply(this);
                    }
                    Thread.sleep(500);
                }
            } finally {
                handle.finish();
                out.close();
            }
        }

        @Override
        public void println(Object o) {
            out.append(String.valueOf(o)).append(LINE_SEPARATOR);
        }

        @Override
        public void begin(int i) {
            work = i;
            handle.switchToDeterminate(i);
        }

        @Override
        public void worked(int i) {
            if (worked < work) {
                worked += i;
                handle.progress(worked);
            }
        }

        @Override
        public boolean isCanceled() {
            return canceled.get();
        }

        @Override
        public void close() {
            canceled.set(true);
        }
    }
}
