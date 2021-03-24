run: compile
	java Frontend

compile: RedBlackTree.java DataWrangler.java Backend.java Frontend.java
	javac Frontend.java

compileTests: TestRedBlackTree.java DataWranglerTests.java FrontEndDeveloperTests.java TestRedBlackTree.java
	javac TestBackend.java DataWranglerTests.java FrontEndDeveloperTest.java TestHashTable.java

test: testRedBlackTree testDataWrangler testBackend testFrontend

testRedBlackTree: compileTests
	java TestRedBlackTree

testDataWrangler: compileTests
	java DataWranglerTests

testBackend: compileTests
	java BackEndDeveloperTests

testFrontend: compileTests
	java FrontEndDeveloperTests

clean:
	$(RM) *.class
