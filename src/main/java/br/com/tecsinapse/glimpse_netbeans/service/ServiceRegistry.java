package br.com.tecsinapse.glimpse_netbeans.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.keyring.Keyring;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;

public class ServiceRegistry extends ChildFactory<GlimpseConnector> {

    private static final String DIR_GLIMPSE_CONNECTORS = "Glimpse/Connectors";
    private static final String NAME_ATTR = "name";
    private static final String URL_ATTR = "url";
    private static final String USER_ATTR = "username";
    private static final ServiceRegistry REGISTRY = new ServiceRegistry();

    public static ServiceRegistry getInstance() {
        return REGISTRY;
    }

    private ServiceRegistry() {
        FileObject glimpseConfDir = getGlimpseDir();

        for (FileObject fileConf : glimpseConfDir.getChildren()) {
            if (fileConf.getAttribute(NAME_ATTR) != null) {
                String name = (String) fileConf.getAttribute(NAME_ATTR);
                String url = (String) fileConf.getAttribute(URL_ATTR);
                String user = (String) fileConf.getAttribute(USER_ATTR);

                String password = new String(Keyring.read(getKeyringName(name)));
                GlimpseLookup.getDefault().add(new GlimpseConnector(name, url, user, password));
            }
        }
    }

    @Override
    protected Node createNodeForKey(GlimpseConnector key) {
        return new ServiceNode(key, Lookups.fixed(this));
    }

    @Override
    protected boolean createKeys(List<GlimpseConnector> toPopulate) {
        Collection<? extends GlimpseConnector> lookupAll = GlimpseLookup.getDefault().lookupAll(GlimpseConnector.class);
        toPopulate.addAll(lookupAll);
        Collections.sort(toPopulate);
        return true;
    }

    public void addService(GlimpseConnector connector) throws IOException {
        FileObject confFile = getConfFile(connector.getName());
        if (confFile != null) {
            throw new IllegalArgumentException("Conf already exists");
        }

        confFile = getGlimpseDir().createData(
                FileUtil.findFreeFileName(getGlimpseDir(),
                "instance", null));
        saveService(confFile, connector);

        GlimpseLookup.getDefault().add(connector);
        refresh(true);
    }

    public GlimpseConnector findService(String connectorName) {
        for (GlimpseConnector glimpseConnector : GlimpseLookup.getDefault().
                lookupAll(GlimpseConnector.class)) {
            if (glimpseConnector.getName().equals(connectorName)) {
                return glimpseConnector;
            }
        }
        return null;
    }

    public void refreshService(GlimpseConnector connector) throws IOException {
        removeService(connector);
        addService(connector);

        refresh(true);
    }

    void removeService(GlimpseConnector connector) throws IOException {
        FileObject confFile = getConfFile(connector.getName());
        confFile.delete();
        Keyring.delete(getKeyringName(connector.getName()));

        GlimpseLookup.getDefault().remove(connector);
        refresh(true);
    }

    public boolean exists(String connectorName) {
        Collection<? extends GlimpseConnector> lookupAll =
                GlimpseLookup.getDefault().lookupAll(GlimpseConnector.class);
        for (GlimpseConnector connector : lookupAll) {
            if (connector.getName().equals(connectorName)) {
                return true;
            }
        }
        return false;
    }

    private FileObject getGlimpseDir() {
        FileObject configDir = FileUtil.getConfigFile(DIR_GLIMPSE_CONNECTORS);
        if (configDir == null) {
            try {
                configDir = FileUtil.createFolder(FileUtil.getConfigRoot(), DIR_GLIMPSE_CONNECTORS);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return configDir;
    }

    private FileObject getConfFile(String connectorName) throws IOException {
        FileObject glimpseConfDir = getGlimpseDir();
        for (FileObject fileObject : glimpseConfDir.getChildren()) {
            if (connectorName.equals(fileObject.getAttribute(NAME_ATTR))) {
                return fileObject;
            }
        }
        return null;
    }

    private String getKeyringName(String name) {
        return "br.com.tecsinapse.glimpse.connector:" + name;
    }

    private void saveService(FileObject confFile, GlimpseConnector connector) throws IOException {
        confFile.setAttribute(NAME_ATTR, connector.getName());
        confFile.setAttribute(URL_ATTR, connector.getUrl());
        confFile.setAttribute(USER_ATTR, connector.getUsername());

        Keyring.save(getKeyringName(connector.getName()), connector.getPassword().toCharArray(),
                "Glimpse Password");
    }
}
