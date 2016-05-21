# build script for project.

function build
{
    echo "mvn clean install -DskipTests"
}   # end of build

function test
{
    echo "mvn test"
}   # end of test

if [ "$1" == "" ]; then
    echo "No argument specified. Select 'build' or 'test'."
elif [ "$1" == "build" ]; then
    echo "Selected mode 'build'"
    $(build)
elif [ "$1" == "test" ]; then
    echo "Selected mode 'test'"
    $(test)
else
    echo "Invalid argument '$1'. Accepted arguments are 'build' or 'test'"
fi
