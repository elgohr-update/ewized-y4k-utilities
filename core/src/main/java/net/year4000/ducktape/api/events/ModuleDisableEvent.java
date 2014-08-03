package net.year4000.ducktape.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.year4000.ducktape.module.AbstractModule;
import net.year4000.ducktape.module.ModuleInfo;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDisableEvent extends Event {
    protected ModuleInfo info;
    protected AbstractModule module;
}
