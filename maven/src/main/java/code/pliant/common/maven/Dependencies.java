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
 * Utility methods for Maven dependancies.
 * 
 * @author Daneil Rugg
 */
public class Dependencies {

	/**
	 * Checks if an artifact is found in the classpath elements.
	 * 
	 * @param artifactId The ID of the artifact.
	 * @param classPathElements Collection of classpath entries.
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
	 * @param session The current MavenSession.
	 * @param groupId The group ID of the Artifact to load.
	 * @param artifactId The artifact ID of the Artifact to load.
	 * @param version The version of the Artifact to load.
	 */
	public static void loadDependency(MavenSession session, String groupId, String artifactId, String version){
		// TODO: Implement
		VersionRange range = VersionRange.createFromVersion(version);
		range.toString();
		//Artifact artifact = new DefaultArtifact(groupId, artifactId, range, scope, "jar", classifier, artifactHandler)
	}
}
