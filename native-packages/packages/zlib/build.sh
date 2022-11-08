PACKAGE_VERSION="1.2.11"
PACKAGE_SRCURL="https://www.zlib.net/zlib-$PACKAGE_VERSION.tar.xz"
PACKAGE_SHA256="4ff941449631ace0d4d203e3483be9dbc9da454084111f97ea0a2114e19bf066"

builder_step_configure() {
	CFLAGS+=" $CPPFLAGS -fPIC"
	LDFLAGS+=" -fPIC"
	"$PACKAGE_SRCDIR"/configure \
		--prefix="$PACKAGE_INSTALL_PREFIX" --static
}
