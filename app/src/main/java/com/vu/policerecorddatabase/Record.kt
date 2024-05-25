package com.vu.policerecorddatabase

class Record{
    var recordId: String? = null
    var recordTitle: String?= null
    var accessLevel: String?= null
    var staffOrCriminal: String?= null
    var description: String?= null

    constructor() // Default constructor required for Firebase

    constructor(
        recordId: String? ,
        recordTitle: String?,
        accessLevel: String?,
        staffOrCriminal: String?,
        description: String?)
    {
        this.recordId = recordId
        this.recordTitle = recordTitle
        this.accessLevel = accessLevel
        this.staffOrCriminal = staffOrCriminal
        this.description = description


    }    }



