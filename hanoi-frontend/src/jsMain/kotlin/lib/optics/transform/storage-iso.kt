package lib.optics.transform

import lib.maths.Maths
import lib.optics.iso.Iso
import lib.optics.storage.Storage

@Maths
operator fun <V, W> Storage<V>.times(iso: Iso<V, W>): Storage<W> = (lens() * iso).storage()
