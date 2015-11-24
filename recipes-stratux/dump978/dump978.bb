DESCRIPTION = "This is a Yocto build recipe for Stratux for installation on the Stratux Yocto image for intel edison. This recipe is for the dump978 portion."
SECTION = "Stratux"
HOMEPAGE = "http://github.com/cyoung/stratux/"

DEPENDS = "rtlsdr"
RDEPENDS_${PN} = "rtlsdr"

RPROVIDES_${PN} += "libdump978"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


SRCREV = "${AUTOREV}"
SRC_URI += "git://github.com/cyoung/stratux.git"

SRC_URI += "file://Makefile"

S = "${WORKDIR}/git/dump978"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"


FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/libdump978.so"
FILES_${PN} += "${bindir}/dump978"

do_compile_prepend() {
    cp ${WORKDIR}/Makefile ${S}/Makefile
}

do_compile() {
   oe_runmake all lib
}

do_install() {
    install -m 0755 -d ${D}${libdir} ${D}${bindir}
    install -m 0644 ${S}/../libdump978.so ${D}${libdir}
    install -m 0755 ${S}/dump978 ${D}${bindir}


}
