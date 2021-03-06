package org.wordpress.android.ui.mediapicker

import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.ui.mediapicker.MediaLibraryDataSource.MediaLibraryDataSourceFactory
import org.wordpress.android.ui.mediapicker.MediaPickerSetup.DataSource.DEVICE
import org.wordpress.android.ui.mediapicker.MediaPickerSetup.DataSource.GIF_LIBRARY
import org.wordpress.android.ui.mediapicker.MediaPickerSetup.DataSource.STOCK_LIBRARY
import org.wordpress.android.ui.mediapicker.MediaPickerSetup.DataSource.WP_LIBRARY
import org.wordpress.android.util.LocaleManagerWrapper
import javax.inject.Inject

class MediaLoaderFactory
@Inject constructor(
    private val deviceListBuilder: DeviceListBuilder,
    private val mediaLibraryDataSourceFactory: MediaLibraryDataSourceFactory,
    private val localeManagerWrapper: LocaleManagerWrapper
) {
    fun build(mediaPickerSetup: MediaPickerSetup, siteModel: SiteModel?): MediaLoader {
        return when (mediaPickerSetup.dataSource) {
            DEVICE -> deviceListBuilder
            WP_LIBRARY -> mediaLibraryDataSourceFactory.build(requireNotNull(siteModel) {
                "Site is necessary when loading WP media library "
            })
            STOCK_LIBRARY -> throw NotImplementedError("Source not implemented yet")
            GIF_LIBRARY -> throw NotImplementedError("Source not implemented yet")
        }.toMediaLoader(mediaPickerSetup)
    }

    private fun MediaSource.toMediaLoader(mediaPickerSetup: MediaPickerSetup) =
            MediaLoader(this, localeManagerWrapper, mediaPickerSetup.allowedTypes)
}
