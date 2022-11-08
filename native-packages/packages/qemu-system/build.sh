PACKAGE_VERSION="5.2.0"
PACKAGE_SRCURL="https://download.qemu.org/qemu-${PACKAGE_VERSION}.tar.xz"
PACKAGE_SHA256="cb18d889b628fbe637672b0326789d9b0e3b8027e0445b936537c78549df17bc"
PACKAGE_DEPENDS="glib, pixman, zlib"
PACKAGE_BUILD_IN_SRC="true"

builder_step_configure() {
	CFLAGS+=" $CPPFLAGS"
	CXXFLAGS+=" $CPPFLAGS"

	if [ "$PACKAGE_TARGET_ARCH" = "i686" ]; then
		LDFLAGS+=" -latomic"
	fi

	# Note: using --disable-stack-protector since stack protector
	# flags already passed by build scripts but we do not want to
	# override them with what QEMU configure provides.
	./configure \
		--prefix="$PACKAGE_INSTALL_PREFIX" \
		--cross-prefix="${PACKAGE_TARGET_PLATFORM}-" \
		--host-cc="gcc" \
		--cc="$CC" \
		--cxx="$CXX" \
		--objcc="$CC" \
		--disable-stack-protector \
		--smbd="/system/bin/smbd" \
		--enable-coroutine-pool \
		--enable-trace-backends=nop \
		--disable-guest-agent \
		--disable-gnutls \
		--disable-nettle \
		--disable-gcrypt \
		--disable-sdl \
		--disable-sdl-image \
		--disable-gtk \
		--disable-vte \
		--disable-curses \
		--disable-iconv \
		--disable-vnc \
		--disable-vnc-sasl \
		--disable-vnc-jpeg \
		--disable-vnc-png \
		--disable-xen \
		--disable-xen-pci-passthrough \
		--enable-virtfs \
		--disable-curl \
		--disable-fdt \
		--disable-kvm \
		--disable-hax \
		--disable-hvf \
		--disable-whpx \
		--disable-libnfs \
		--disable-libusb \
		--disable-lzo \
		--disable-snappy \
		--disable-bzip2 \
		--disable-lzfse \
		--disable-seccomp \
		--disable-libssh \
		--disable-libxml2 \
		--disable-bochs \
		--disable-cloop \
		--disable-dmg \
		--disable-parallels \
		--disable-qed \
		--disable-sheepdog \
		--target-list=x86_64-softmmu
}

builder_step_post_make_install() {
	local bindir

	case "$PACKAGE_TARGET_ARCH" in
		aarch64) bindir="arm64-v8a";;
		arm) bindir="armeabi-v7a";;
		i686) bindir="x86";;
		x86_64) bindir="x86_64";;
		*) echo "Invalid architecture '$PACKAGE_TARGET_ARCH'" && return 1;;
	esac

	install -Dm600 "$PACKAGE_INSTALL_PREFIX"/lib/libqemu-system-x86_64.so \
		"${BUILDER_SCRIPTDIR}/jniLibs/${bindir}/libvshell-engine.so"
	"$STRIP" -s "${BUILDER_SCRIPTDIR}/jniLibs/${bindir}/libvshell-engine.so"
}
