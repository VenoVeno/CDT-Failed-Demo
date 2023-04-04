import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;

import org.eclipse.cdt.core.model.ILanguage;

import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;

public class SampleParser {
    public static void main(String[] args) {
        generateTranslationUnit("example/macro.c");
    }

    private static void generateTranslationUnit(String fileName) {
        FileContent fileContent = FileContent.createForExternalFileLocation(fileName);

        Map<String, String> definedSymbols = new HashMap<>();

        String[] includePaths = new String[] {};
        IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);

        IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();
        int opts = ILanguage.OPTION_PARSE_INACTIVE_CODE;

        IParserLogService log = new DefaultLogService();

        IASTTranslationUnit translationUnit = null;

        try {
            translationUnit = GCCLanguage.getDefault().getASTTranslationUnit(fileContent, info,
                    emptyIncludes, null, opts, log);

            if (translationUnit != null) {
                print(translationUnit, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(IASTNode node, int index) {
        IASTNode[] children = node.getChildren();

        // PRINT ALL TO CONSOLE
        System.out.println(
                String.format(
                        new StringBuilder("%1$").append(index * 2).append("s").toString(), // NO I18N
                        new Object[] { "- " })
                        + node.getClass().getSimpleName());

        // RECURSION
        for (IASTNode iastNode : children) {
            print(iastNode, index + 1);
        }
    }
}
