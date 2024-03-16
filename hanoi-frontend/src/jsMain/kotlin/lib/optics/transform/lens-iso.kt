package lib.optics.transform

import lib.maths.Maths
import lib.maths.o
import lib.optics.iso.Iso
import lib.optics.lens.Lens


@Maths
operator fun <W, P, Q> Lens<W, P>.times(iso: Iso<P, Q>): Lens<W, Q> = Lens(
    iso.forth o get,
    set o iso.back
)

@Maths
operator fun <V, W, P,> Iso<V, W>.times(lens: Lens<W, P>): Lens<V, P> = Lens(
    lens.get o forth
) {
        p -> back o lens.set(p) o forth
}