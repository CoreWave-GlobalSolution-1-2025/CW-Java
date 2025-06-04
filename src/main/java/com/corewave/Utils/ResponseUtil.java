package com.corewave.Utils;

import com.corewave.dtos.ExceptionDto;
import com.corewave.dtos.PageDto;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ResponseUtil {

    public enum Texts {
        NOT_FOUND("Objeto não encontrado. Verifique se o 'ID'"),
        UPDATE_MISSING_FIELDS("Insira algum valor para atualizar. Os campos estão vazios."),
        CREATE_MISSING_FIELDS("Verifique se os campos estão preenchidos corretamente. Certifique-se que o campo 'id' está vazio"),
        SERVER_ERROR_ADD("Erro ao adicionar objeto. Verifique os logs do sistema para mais detalhes."),
        SERVER_ERROR_GET("Erro ao recuperar objeto. Verifique os logs do sistema para mais detalhes."),
        SERVER_ERROR_UPDATE("Erro ao atualizar objeto. Verifique os logs do sistema para mais detalhes."),
        SERVER_ERROR_DELETE("Erro ao deletar objeto. Verifique os logs do sistema para mais detalhes."),
        ;

         final String value;

        Texts(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public static Response createExceptionResponse(Response.Status status, String text) {
        var response = Response.status(status)
                .header("Content-Type", "application/json")
                .entity(new ExceptionDto(
                        status.getStatusCode(),
                        text,
                        status.getReasonPhrase()
                ));

        return response.build();
    }

    public static Response createPaginatedResponse(int page, int pageSize, List<?> list) {
        var start = (page - 1) * pageSize;
        var end = Math.min(list.size(), (start + pageSize) - 1);

        var listPaginated = list.subList(start, end);

        return Response.ok(
                new PageDto<>(
                        page,
                        pageSize,
                        list.size(),
                        listPaginated
                )
        ).build();
    }
}
