package net.sf.jabref.gui.importer.actions;

import java.util.Collections;
import java.util.Optional;

import net.sf.jabref.Globals;
import net.sf.jabref.gui.BasePanel;
import net.sf.jabref.logic.groups.AllEntriesGroup;
import net.sf.jabref.logic.groups.ExplicitGroup;
import net.sf.jabref.logic.groups.GroupHierarchyType;
import net.sf.jabref.logic.groups.GroupTreeNode;
import net.sf.jabref.logic.importer.ParserResult;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.preferences.JabRefPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConvertLegacyExplicitGroupsTest {

    private ConvertLegacyExplicitGroups action;
    @Mock private BasePanel basePanel;
    private BibEntry entry;
    private ExplicitGroup group;

    @Before
    public void setUp() throws Exception {
        Globals.prefs = JabRefPreferences.getInstance();

        action = new ConvertLegacyExplicitGroups();

        entry = new BibEntry();
        entry.setCiteKey("Entry1");
        group = new ExplicitGroup("TestGroup", GroupHierarchyType.INCLUDING,
                JabRefPreferences.getInstance().get(JabRefPreferences.KEYWORD_SEPARATOR));
        group.addLegacyEntryKey("Entry1");
    }

    @Test
    public void performActionWritesGroupMembershipInEntry() throws Exception {
        ParserResult parserResult = generateParserResult(entry, GroupTreeNode.fromGroup(group));

        action.performAction(basePanel, parserResult);

        assertEquals(Optional.of("TestGroup"), entry.getField("groups"));
    }

    @Test
    public void performActionClearsLegacyKeys() throws Exception {
        ParserResult parserResult = generateParserResult(entry, GroupTreeNode.fromGroup(group));

        action.performAction(basePanel, parserResult);

        assertEquals(Collections.emptyList(), group.getLegacyEntryKeys());
    }

    @Test
    public void performActionWritesGroupMembershipInEntryForComplexGroupTree() throws Exception {
        GroupTreeNode root = GroupTreeNode.fromGroup(new AllEntriesGroup());
        root.addSubgroup(new ExplicitGroup("TestGroup2", GroupHierarchyType.INCLUDING,
                JabRefPreferences.getInstance().get(JabRefPreferences.KEYWORD_SEPARATOR)));
        root.addSubgroup(group);
        ParserResult parserResult = generateParserResult(entry, root);

        action.performAction(basePanel, parserResult);

        assertEquals(Optional.of("TestGroup"), entry.getField("groups"));
    }

    @Test
    public void isActionNecessaryReturnsTrueIfGroupContainsLegacyKeys() throws Exception {
        ParserResult parserResult = generateParserResult(entry, GroupTreeNode.fromGroup(group));

        assertTrue(action.isActionNecessary(parserResult));
    }

    private ParserResult generateParserResult(BibEntry entry, GroupTreeNode groupRoot) {
        ParserResult parserResult = new ParserResult(Collections.singletonList(entry));
        parserResult.getMetaData().setGroups(groupRoot);
        return parserResult;
    }
}
