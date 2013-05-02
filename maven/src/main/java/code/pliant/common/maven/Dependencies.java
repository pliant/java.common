/**
 * 
 */
package code.pliant.common.maven;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.execution.MavenSession;


/**
 * Utility methods for Maven
 * 
 * @author Daneil Rugg
 */
public class Dependencies {

	/**
	 * Checks if an artifact is found in the classpath elements.
	 * 
	 * @param artifactId
	 * @param classPathElements
	 */
	public static boolean isArtifactInClasspath(String artifactId, Collection<String> classPathElements){
		Pattern pattern = Pattern.compile("^.*/" + artifactId + "[^/]+.jar$");
		if (classPathElements != null) {
            for (String e : classPathElements) {
                Matcher m = pattern.matcher(e);
                if (m.matches())
                    return true;
            }
        }
		return false;
	}
	
	/**
	 * Forces Maven to find a dependency and load it into the repository for use.
	 * 
	 * @param session
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @return
	 */
	public static String loadDependency(MavenSession session, String groupId, String artifactId, String version){
		VersionRange range = VersionRange.createFromVersion(version);
		range.toString();
		//Artifact artifact = new DefaultArtifact(groupId, artifactId, range, scope, "jar", classifier, artifactHandler)
		return null;
	}
}
