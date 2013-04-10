package br.com.tecsinapse.glimpse_netbeans.service;

import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class GlimpseLookup extends AbstractLookup {

    public static GlimpseLookup getDefault() {
        if (lookup == null) {
            lookup = new GlimpseLookup(new InstanceContent());
        }
        return lookup;
    }
    private static GlimpseLookup lookup;
    private final InstanceContent content;

    private GlimpseLookup(InstanceContent content) {
        super(content);
        this.content = content;
    }

    void add(Object instance) {
        content.add(instance);
    }

    void remove(Object instance) {
        content.remove(instance);
    }
}
