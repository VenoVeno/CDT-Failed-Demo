import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;

import org.eclipse.cdt.core.model.ILanguage;

import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;

import org.eclipse.cdt.internal.core.dom.rewrite.astwriter.ASTWriter;

public class SampleParser {
    public static void main(String[] args) {
        IASTTranslationUnit translationUnit = generateTranslationUnit("example/Hello.c");
        System.out.println(new ASTWriter().write(translationUnit));
    }

    private static IASTTranslationUnit generateTranslationUnit(String fileName) {
        FileContent fileContent = FileContent.createForExternalFileLocation(fileName);

        Map<String, String> definedSymbols = new HashMap<>();

        String[] includePaths = new String[0];
        IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);

        IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();
        int opts = ILanguage.OPTION_IS_SOURCE_UNIT;

        IParserLogService log = new DefaultLogService();

        IASTTranslationUnit translationUnit = null;

        try {
            translationUnit = GCCLanguage.getDefault().getASTTranslationUnit(fileContent, info,
                    emptyIncludes, null, opts, log);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return translationUnit;
    }
}
