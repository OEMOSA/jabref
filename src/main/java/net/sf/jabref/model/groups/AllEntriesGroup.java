package net.sf.jabref.model.groups;

import java.util.List;
import java.util.Optional;

import net.sf.jabref.logic.groups.GroupDescriptions;
import net.sf.jabref.logic.l10n.Localization;
import net.sf.jabref.model.EntriesGroupChange;
import net.sf.jabref.model.entry.BibEntry;

/**
 * This group contains all entries. Always. At any time!
 */
public class AllEntriesGroup extends AbstractGroup {

    public static final String ID = "AllEntriesGroup:";

    public AllEntriesGroup() {
        super(Localization.lang("All entries"), GroupHierarchyType.INDEPENDENT);
    }

    public static AbstractGroup fromString(String s) {
        if (!s.startsWith(AllEntriesGroup.ID)) {
            throw new IllegalArgumentException("AllEntriesGroup cannot be created from \"" + s + "\".");
        }
        return new AllEntriesGroup();
    }

    @Override
    public boolean supportsAdd() {
        return false;
    }

    @Override
    public boolean supportsRemove() {
        return false;
    }

    @Override
    public Optional<EntriesGroupChange> add(List<BibEntry> entriesToAdd) {
        // not supported -> ignore
        return Optional.empty();
    }

    @Override
    public Optional<EntriesGroupChange> remove(List<BibEntry> entriesToRemove) {
        // not supported -> ignore
        return Optional.empty();
    }

    @Override
    public AbstractGroup deepCopy() {
        return new AllEntriesGroup();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AllEntriesGroup;
    }

    @Override
    public String toString() {
        return AllEntriesGroup.ID;
    }

    @Override
    public boolean contains(BibEntry entry) {
        return true;
    }

    @Override
    public boolean isDynamic() {
        // this is actually a special case; I define it as non-dynamic
        return false;
    }

    @Override
    public String getDescription() {
        return GroupDescriptions.getDescriptionAllEntriesGroup();
    }

    @Override
    public String getShortDescription(boolean showDynamic) {
        return GroupDescriptions.getShortDescriptionAllEntriesGroup();
    }

    @Override
    public String getTypeId() {
        return AllEntriesGroup.ID;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
}
