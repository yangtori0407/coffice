$('#updateModal').on('show.bs.modal', function (event) {
  const button = $(event.relatedTarget);
  const modal = $(this);

  const userId = button.data('userid');
  const attendanceDate = button.data('date');   
  const start = button.data('start')?.substring(0, 5); // "08:21:35" → "08:21"
  const end = button.data('end')?.substring(0, 5); 
  const status = button.data('status');
  const reason = button.data('reason');

  modal.find('#modalUserId').val(userId);
  modal.find('#modalAttendanceDate').val(attendanceDate);
  modal.find('#modalStartTime').val(start);
  modal.find('#modalEndTime').val(end);
  modal.find('#modalReason').val(reason);

  modal.find('input[name="status"]').prop('checked', false);
  modal.find('input[name="status"][value="' + status + '"]').prop('checked', true);
});

