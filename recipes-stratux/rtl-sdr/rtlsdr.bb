DESCRIPTION = "This is a Yocto build recipe for librtlsdr Stratux Yocto image for intel edison"
SECTION = "libs"
HOMEPAGE = "http://sdr.osmocom.org/trac/wiki/rtl-sdr"

DEPENDS = "libusb"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/steve-m/librtlsdr.git"

inherit autotools pkgconfig
S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=751419260aa954499f7abaabaa882bbe"

