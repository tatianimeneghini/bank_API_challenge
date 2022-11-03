package com.donus.`code-challenge`.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class BankAccountException(override val message: String): Exception(message)
