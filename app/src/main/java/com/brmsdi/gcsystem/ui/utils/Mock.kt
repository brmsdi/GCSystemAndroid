package com.brmsdi.gcsystem.ui.utils

import com.brmsdi.gcsystem.data.model.Status

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class Mock {

    companion object {

        fun statusList() = mutableListOf(
            Status(1, "Em aberto"),
            Status(2, "Disponível"),
            Status(3, "Em andamento"),
            Status(4, "Concluído")
        )
    }
}