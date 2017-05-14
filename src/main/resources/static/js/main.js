//var persons = null;
//var self = this;

$(document).ready(function() {

    //persons  = getPersons();

    $('#get-person-form').submit(function(event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        getPersons();

    });

    $('#create-new-person-form').submit(function(event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        createPerson();

    });

    $('#add-person-div-link').click(function(evt) {
        clearMainContainer();
        $('#add-person-div').removeClass('hidden');
    });

    $('#get-all-persons-div-link').click(function(evt) {
        clearMainContainer();
        $('#get-all-persons-div').removeClass('hidden');
    });

    $('#update-existing-person-checkbox').click(function() {
        // if ($('#update-existing-person-checkbox').is(':checked')) {
        //     console.log('checked');
        //     console.log('persons', self.persons);
        //     console.log('size', self.persons.length);
        //     if (self.persons) {
        //         var i;
        //         for (i = 0; i < self.persons.length; ++i) {
        //             console.log('person: ', self.persons[i]);
        //         }
        //     }
        // } else {
        //     console.log('not checked!');
        // }
        //console.log(_);
    });

});

function createDropdownOfPersons() {
    var persons = getPersons();
    console.log(persons);
}


function clearMainContainer() {
    $('#add-person-div').addClass('hidden');
    $('#get-all-persons-div').addClass('hidden');
}

function getAndValidateDate(dateType) {

    // TODO Actually validate the date.

    if (!$('#create-new-person-' + dateType + '-date-known').is(':checked')) return null;

    var year = $('#create-new-person-' + dateType + '-date-year').val();
    var month = $('#create-new-person-' + dateType + '-date-month').val();
    var day = $('#create-new-person-' + dateType + '-date-day').val();

    var date = new Date(year, month, day);

    return date;
}

function getPersons() {

    $('#btn-search').prop('disabled', true);

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: '/person/all',
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = '<h4>Ajax Response</h4><pre>'
                + JSON.stringify(data, null, 4) + '</pre>';
            $('#feedback').html(json);

            console.log('SUCCESS : ', data);
            $('#btn-search').prop('disabled', false);

        },
        error: function (e) {

            var json = '<h4>Ajax Response</h4><pre>'
                + e.responseText + '</pre>';
            $('#feedback').html(json);

            console.log('ERROR : ', e);
            $('#btn-search').prop('disabled', false);

        }
    });

}

function createPerson() {
    var search = {
        firstName: $('#create-new-person-first-name').val(),
        middleNames: $('#create-new-person-middle-names').val(),
        lastName: $('#create-new-person-last-name').val(),
        suffix: $('#create-new-person-suffix').val(),
        maidenName: $('#create-new-person-maiden-name').val(),
        maidenName: $('#create-new-person-maiden-name').val(),
        birthplace: $('#create-new-person-birth-place').val(),
        familyBranch: $('#create-new-person-family-branch').val(),
        birthdate: getAndValidateDate('birth'),
        deathdate: getAndValidateDate('death')
    };

    $('#btn-search').prop('disabled', true);

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '/person',
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = '<h4>Ajax Response</h4><pre>'
                + JSON.stringify(data, null, 4) + '</pre>';
            $('#feedback').html(json);

            console.log('SUCCESS : ', data);
            $('#btn-search').prop('disabled', false);

            //self.persons  = getPersons();

        },
        error: function (e) {

            var json = '<h4>Ajax Response</h4><pre>'
                + e.responseText + '</pre>';
            $('#feedback').html(json);

            console.log('ERROR : ', e);
            $('#btn-search').prop('disabled', false);

        }
    });

}